package com.myapplicationdev.android.p10_ndpsongs_clv;

import java.io.Serializable;

    public class Year implements Serializable {
        private int year;

        public Year(int year) {
            this.year = year;
        }

        public String toString() {
            return String.valueOf(year);
        }
    }

