package com.tuiyi.allin.core.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;


/**
 * 广告次数
 */
public class AllInDatabase {

    private final AllInDatabaseHelper dbHelper;

    public AllInDatabase(Context context) {
        super();
        dbHelper = new AllInDatabaseHelper(context);
    }

    /**
     * 下载缓存表
     */
    public static final class AD {

        /**
         * 字段
         */
        public static final class Columns {
            public static final String ID = "_id";
            public static final String UNITID = "unitid";
            public static final String DATE = "crdate";
        }

        /**
         * 表名
         */
        public static final String TABLE_NAME = "allin_ad";

        /**
         * 创建表的SQL
         */
        public static final String CREATE_SQL = "create table " + TABLE_NAME
                + " (" + Columns.ID + " integer primary key autoincrement,"
                + Columns.UNITID + " varchar(60)," + Columns.DATE + " long(30)"
                + ")";

        /**
         * 删除表的SQL
         */
        public static final String DROP_SQL = "drop table if exists " + TABLE_NAME;

        /**
         * 添加一条数据的SQL
         */
        public static final String INSERT_SQL = "insert into " + TABLE_NAME
                + " (" + Columns.UNITID + "," + Columns.DATE
                + ") values (?,?)";

        /**
         * 删除某个时间之前的记录
         */
        public static final String DELETE_SQL = "delete from " + TABLE_NAME + " where " + Columns.DATE + "<?";


        /**
         * 根据unitid，查询某个时间的数据的SQL
         */
        public static final String QUERY_HOUR_SQL = "select count(*) from " + TABLE_NAME
                + " where " + Columns.UNITID + "=?" + " and " + Columns.DATE + ">?";

        /**
         * 根据unitid，查询数据的SQL
         */
        public static final String QUERY_SQL = "select * from " + TABLE_NAME
                + " where " + Columns.UNITID + "=?";
        /**
         * 删除所有数据的SQL
         */
        public static final String DELETE_ALL_SQL = "delete from " + TABLE_NAME;
    }

    /**
     * 插入数据
     */
    public void insert(String unitid, long currentTime) {
        String sql = AD.INSERT_SQL;
        SQLiteDatabase sqlite = dbHelper.getWritableDatabase();
        sqlite.execSQL(sql, new Object[]{unitid, currentTime});
        sqlite.close();
    }


    /**
     * 删除某个时间之前的记录
     *
     * @param time 时间戳
     */
    public void delete(long time) {
        SQLiteDatabase sqlite = dbHelper.getWritableDatabase();
        sqlite.execSQL(AD.DELETE_SQL, new Object[]{time});
        sqlite.close();
    }

    /**
     * 删除所有数据
     */
    public void deleteAll() {
        String sql = AD.DELETE_ALL_SQL;
        SQLiteDatabase sqlite = dbHelper.getWritableDatabase();
        sqlite.execSQL(sql);
        sqlite.close();
    }

    /**
     * 查询某个时间之后展示次数
     *
     * @param queryTime 时间戳
     * @return count 次数
     */
    public int getCountByTime(String unitId, Long queryTime) {
        if (TextUtils.isEmpty(unitId) || queryTime == null) {
            return 0;
        }
        String sql = AD.QUERY_HOUR_SQL;
        SQLiteDatabase sqlite = dbHelper.getReadableDatabase();
        Cursor cursor = sqlite.rawQuery(sql, new String[]{unitId, queryTime.toString()});
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        if (!cursor.isClosed()) {
            cursor.close();
        }
        sqlite.close();
        return count;
    }

    /**
     * 查询最新数据
     *
     * @return
     */
    public long getLastTime(String unitId) {
        if (TextUtils.isEmpty(unitId)) {
            return 0;
        }
        long lastTime = 0;
        String sql = AD.QUERY_SQL;
        SQLiteDatabase sqlite = dbHelper.getReadableDatabase();
        Cursor cursor = sqlite.rawQuery(sql, new String[]{unitId});
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            lastTime = (cursor.getLong(cursor.getColumnIndex(AD.Columns.DATE)));
        }
        if (!cursor.isClosed()) {
            cursor.close();
        }
        sqlite.close();
        return lastTime;
    }

    public void destroy() {
        dbHelper.close();
    }

}