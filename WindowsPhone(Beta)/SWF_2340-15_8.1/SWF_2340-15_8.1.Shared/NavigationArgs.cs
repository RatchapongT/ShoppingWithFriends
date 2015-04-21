using System;
using System.Collections.Generic;
using System.Text;

namespace SWF_2340_15_8._1
{
    class NavigationArgs
    {
        public User currUser { get; set; }
        public User aUser { get; set; }
        public Report aReport { get; set; }
        public Request aRequest { get; set; }
        public Type sender { get; set; }
    }
}