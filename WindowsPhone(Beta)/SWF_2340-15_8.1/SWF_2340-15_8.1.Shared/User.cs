using System;
using System.Collections.Generic;
using System.Text;
using SQLite;
namespace SWF_2340_15_8._1
{
    [Table("Users")]
    class User
    {
        [PrimaryKey, AutoIncrement]
        public int id { get; set; }
        public string Name { get; private set; }
        public string Username { get; private set; }
        public string Email { get; private set; }
        public string Password { get; private set; }
        public string friendsList { get; set; }
        public string Ratings { get; private set; }

        public User() { }

        public User(string name, string user, string email, string password)
        {
            Name = name;
            Username = user;
            Email = email;
            Password = password;
            Ratings = "";
            friendsList = "";
        }

        public User(string name, string user, string email, string ratings, string friends)
        {
            Name = name;
            Username = user;
            Email = email;
            Ratings = ratings;
            friendsList = friends;
        }

        public double getRating()
        {
            string[] rats = Ratings.Split(',');
            if (Ratings == "" ) return 0.0;
            else
            {
                double sum = 0.0;
                foreach (string s in rats)
                {
                    double temp = Convert.ToDouble(s);
                    sum += temp;
                }
                if (sum == 0) return 0.0;
                else return (double) sum / rats.Length;
            }
        }
    }
}
