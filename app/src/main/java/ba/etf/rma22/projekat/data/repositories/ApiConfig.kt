package ba.etf.rma22.projekat.data.repositories

class ApiConfig {

    companion object{

        var baseURL : String = "https://rma22ws.herokuapp.com"


        fun postaviBaseURL(baseURL: String ) : Unit {
            this.baseURL = baseURL
        }
    }
}

