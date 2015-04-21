using SWF_2340_15_8._1.Common;
using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Runtime.InteropServices.WindowsRuntime;
using Windows.Foundation;
using Windows.Foundation.Collections;
using Windows.Graphics.Display;
using Windows.UI.ViewManagement;
using Windows.UI.Xaml;
using Windows.UI.Xaml.Controls;
using Windows.UI.Xaml.Controls.Primitives;
using Windows.UI.Xaml.Data;
using Windows.UI.Xaml.Input;
using Windows.UI.Xaml.Media;
using Windows.UI.Xaml.Navigation;
using SQLite;
using System.Collections.ObjectModel;
using System.Threading.Tasks;

// The Basic Page item template is documented at http://go.microsoft.com/fwlink/?LinkID=390556

namespace SWF_2340_15_8._1
{
    /// <summary>
    /// An empty page that can be used on its own or navigated to within a Frame.
    /// </summary>
    public sealed partial class MainMenu : Page
    {
        private NavigationHelper navigationHelper;
        private ObservableDictionary defaultViewModel = new ObservableDictionary();
        private User currUser;
        ObservableCollection<User> friendCollection = new ObservableCollection<User>();
        ObservableCollection<Request> reqCollection = new ObservableCollection<Request>();
        ObservableCollection<Report> repCollection = new ObservableCollection<Report>();
        List<Request> userRequests = new List<Request>();
        SQLiteAsyncConnection conn = new SQLiteAsyncConnection("appData.db");
        private bool repSet;

        public MainMenu()
        {
            this.InitializeComponent();

            this.navigationHelper = new NavigationHelper(this);
            this.navigationHelper.LoadState += this.NavigationHelper_LoadState;
            this.navigationHelper.SaveState += this.NavigationHelper_SaveState;
            FrLst.DataContext = friendCollection;
            RepLst.DataContext = repCollection;
            ReqLst.DataContext = reqCollection;
        }

        /// <summary>
        /// Gets the <see cref="NavigationHelper"/> associated with this <see cref="Page"/>.
        /// </summary>
        public NavigationHelper NavigationHelper
        {
            get { return this.navigationHelper; }
        }

        /// <summary>
        /// Gets the view model for this <see cref="Page"/>.
        /// This can be changed to a strongly typed view model.
        /// </summary>
        public ObservableDictionary DefaultViewModel
        {
            get { return this.defaultViewModel; }
        }

        /// <summary>
        /// Populates the page with content passed during navigation.  Any saved state is also
        /// provided when recreating a page from a prior session.
        /// </summary>
        /// <param name="sender">
        /// The source of the event; typically <see cref="NavigationHelper"/>
        /// </param>
        /// <param name="e">Event data that provides both the navigation parameter passed to
        /// <see cref="Frame.Navigate(Type, Object)"/> when this page was initially requested and
        /// a dictionary of state preserved by this page during an earlier
        /// session.  The state will be null the first time a page is visited.</param>
        private async void NavigationHelper_LoadState(object sender, LoadStateEventArgs e)
        {
            var navArgs = (NavigationArgs)e.NavigationParameter;
            currUser = navArgs.currUser;
            await getFriends();
            await getRequests();
            repSet = true;
            await getReports();
        }

        /// <summary>
        /// Preserves state associated with this page in case the application is suspended or the
        /// page is discarded from the navigation cache.  Values must conform to the serialization
        /// requirements of <see cref="SuspensionManager.SessionState"/>.
        /// </summary>
        /// <param name="sender">The source of the event; typically <see cref="NavigationHelper"/></param>
        /// <param name="e">Event data that provides an empty dictionary to be populated with
        /// serializable state.</param>
        private void NavigationHelper_SaveState(object sender, SaveStateEventArgs e)
        {
        }

        #region NavigationHelper registration

        /// <summary>
        /// The methods provided in this section are simply used to allow
        /// NavigationHelper to respond to the page's navigation methods.
        /// <para>
        /// Page specific logic should be placed in event handlers for the  
        /// <see cref="NavigationHelper.LoadState"/>
        /// and <see cref="NavigationHelper.SaveState"/>.
        /// The navigation parameter is available in the LoadState method 
        /// in addition to page state preserved during an earlier session.
        /// </para>
        /// </summary>
        /// <param name="e">Provides data for navigation methods and event
        /// handlers that cannot cancel the navigation request.</param>
        protected override void OnNavigatedTo(NavigationEventArgs e)
        {
            var navArgs = (NavigationArgs)e.Parameter;
            if (navArgs.sender.Equals(typeof(login))) this.Frame.BackStack.Clear();
            this.navigationHelper.OnNavigatedTo(e);
        }

        protected override void OnNavigatedFrom(NavigationEventArgs e)
        {
            this.navigationHelper.OnNavigatedFrom(e);
        }

        #endregion

        private void logout_Click(object sender, RoutedEventArgs e)
        {
            this.Frame.Navigate(typeof(MainPage));
        }

        private void AddFriend_Click(object sender, RoutedEventArgs e)
        {
            this.Frame.Navigate(typeof(NewFriend), new NavigationArgs() { currUser = this.currUser });
        }

        private void AddReq_Click(object sender, RoutedEventArgs e)
        {
            this.Frame.Navigate(typeof(NewRequest), new NavigationArgs() { currUser = this.currUser });
        }

        private void AddRep_Click(object sender, RoutedEventArgs e)
        {
            this.Frame.Navigate(typeof(NewReport), new NavigationArgs() { currUser = this.currUser });
        }

        private async Task getFriends()
        {
            friendCollection.Clear();
            await conn.CreateTableAsync<User>();
            string friendList = currUser.friendsList;
            string[] friendsArr = friendList.Split(',');
            foreach (string s in friendsArr)
            {
                var friend = await conn.Table<User>().Where(x => x.Username == s).FirstOrDefaultAsync();
                if (friend != null) 
                    if (!friendCollection.Contains(friend)) friendCollection.Add(friend);
            }
        }

        private async Task getReports()
        {
            repCollection.Clear();
            await conn.CreateTableAsync<Report>();
            var reports = await conn.Table<Report>().ToListAsync();
            foreach (var i in reports)
            {
                if (currUser.friendsList.Contains(i.owner))
                {
                    foreach (Request r in userRequests)
                    {
                        if (i.item.Equals(r.item) && i.price <= r.maxPrice)
                        {
                            if (!repCollection.Contains(i)) repCollection.Add(i);
                        }
                    }
                }
            }
        }

        private async Task getRequests()
        {
            reqCollection.Clear();
            await conn.CreateTableAsync<Request>();
            var requests = await conn.Table<Request>().ToListAsync();
            foreach (var i in requests)
            {
                if (i.owner == currUser.Username && !userRequests.Contains(i)) userRequests.Add(i);
                if (!reqCollection.Contains(i)) reqCollection.Add(i);
            }
        }

        private async void Navigation_SelectionChanged(object sender, SelectionChangedEventArgs e)
        {
            AddReq.Visibility = Visibility.Collapsed;
            AddRep.Visibility = Visibility.Collapsed;
            AddFriend.Visibility = Visibility.Collapsed;
            logout.Visibility = Visibility.Collapsed;
            switch(Navigation.SelectedIndex)
            {
                case 0:
                    AddFriend.Visibility = Visibility.Collapsed;
                    AddReq.Visibility = Visibility.Collapsed;
                    AddRep.Visibility = Visibility.Visible;
                    logout.Visibility = Visibility.Visible;
                    if (repSet) repSet = false;
                    else await getReports();
                    break;
                case 1:
                    AddFriend.Visibility = Visibility.Collapsed;
                    AddReq.Visibility = Visibility.Visible;
                    AddRep.Visibility = Visibility.Collapsed;
                    logout.Visibility = Visibility.Visible;
                    await getRequests();
                    break;
                case 2:
                    AddReq.Visibility = Visibility.Collapsed;
                    AddRep.Visibility = Visibility.Collapsed;
                    AddFriend.Visibility = Visibility.Visible;
                    logout.Visibility = Visibility.Visible;
                    await getFriends();
                    break;
                default:
                    break;
            }
        }

        private void userSelected(object sender, ItemClickEventArgs e)
        {
            User clicked = (User)e.ClickedItem;
            this.Frame.Navigate(typeof(FriendView), new NavigationArgs() { aUser = clicked, currUser = this.currUser });
        }

        private void reqClick(object sender, ItemClickEventArgs e)
        {
            Request clicked = (Request)e.ClickedItem;
            this.Frame.Navigate(typeof(RequestView), new NavigationArgs() { aRequest = clicked, currUser = this.currUser });
        }

        private void repClick(object sender, ItemClickEventArgs e)
        {
            Report clicked = (Report)e.ClickedItem;
            this.Frame.Navigate(typeof(ReportView), new NavigationArgs() { aReport = clicked, currUser = this.currUser });
        }
    }
}
