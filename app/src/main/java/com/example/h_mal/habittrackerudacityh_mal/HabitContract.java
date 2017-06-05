package com.example.h_mal.habittrackerudacityh_mal;

import android.provider.BaseColumns;

/**
 * Created by h_mal on 26/03/2017.
 */

public class HabitContract {

    private HabitContract() {}

    public static final class HabitEntry implements BaseColumns {

        public final static String TABLE_NAME = "Habits";


        public final static String _ID = BaseColumns._ID;


        public final static String COLUMN_HABIT_NAME ="name";


        public final static String COLUMN_HABIT_FREQUENCY = "frequency";

    }

}
