package thuy.ptithcm.string.database

import androidx.room.RoomDatabase


//@Database(entities = [UserInfo::class], version = 2)
abstract class StringDatabase : RoomDatabase() {
//    abstract fun userDao(): UserDao
//
//    companion object {
//        private var sInstance: StringDatabase? = null
//
//        fun get(context: Context): StringDatabase? {
//            // tránh tạo nhiều databse
//            if (sInstance == null) {
//                // chạy đồng bộ, chỉ có 1 class truy cập tại 1 thời điểm
//                // khi 1 class truy cập nó sẽ lock lại, khi nào thực hiện xong sẽ unlock
//                synchronized(StringDatabase::class) {
//                    sInstance = Room.databaseBuilder(context,
//                        StringDatabase::class.java,
//                        DATABASE_NAME)
//                        .fallbackToDestructiveMigration()
//                        .build()
//                }
//            }
//            return sInstance
//        }
//    }

}