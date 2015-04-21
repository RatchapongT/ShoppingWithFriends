using System;
using System.Collections.Generic;
using System.Text;
using SQLite;

namespace SWF_2340_15_8._1
{
    [Table("Reports")]
    class Report
    {
        [PrimaryKey, AutoIncrement]
        public int id { get; set; }
        public string owner { get; private set; }
        public string item { get; private set; }
        public double price { get; private set; }
        public string location { get; private set; }
        public double latitude { get; private set; }
        public double longitude { get; private set; }
        public string notes { get; private set; }

        public Report()
        {

        }

        public Report(String owner, String item, double price, String location)
        {
            this.owner = owner;
            this.item = item;

            this.price = price;
            this.location = location;
        }
        public Report(String owner, String item, double price, double lat, double lon, string notes)
        {
            this.owner = owner;
            this.item = item;
            this.notes = notes;
            this.price = price;
            this.latitude = lat;
            this.longitude = lon;
        }
    }
}
