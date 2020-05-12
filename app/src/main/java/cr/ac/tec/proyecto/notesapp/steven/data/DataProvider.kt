package cr.ac.tec.proyecto.notesapp.steven.data

import cr.ac.tec.proyecto.notesapp.steven.data.mysql.MySqlDataManager


class DataProvider private constructor() {
    var dataManager: MySqlDataManager =
        MySqlDataManager()

    companion object{
        @JvmStatic
        private var instance: DataProvider? = null

        @JvmStatic
        fun getInstance(): DataProvider{
            if(instance ==null)
                instance = DataProvider()
            return  instance!!
        }

    }
}