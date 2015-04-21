using System;
using System.Collections.Generic;
using System.Text;
using SQLite;

namespace SWF_2340_15_8._1
{
    [Table("Requests")]
    class Request
    {
        [PrimaryKey, AutoIncrement]
        public int id { get; set; }
        public string owner { get; private set; }
        public string item { get; private set; }
        public double maxPrice { get; private set; }

        public Request()
        {

        }

        public Request(string currUser)
        {
            owner = currUser;
            //item = null;
        }

        public Request(String owner, String item, double maxPrice)
        {
            this.owner = owner;
            this.item = item;
            this.maxPrice = maxPrice;
        }
    }
}
