package ba.etf.rma22.projekat.data.repositories

import ba.etf.rma22.projekat.BuildConfig


class AccountRepository {
    companion object{
        var acHash : String = BuildConfig.HASH_KEY
        fun postaviHash(acHash: String):Boolean {
            this.acHash = acHash
            return acHash.isNotEmpty()
        }
        fun getHash(): String{
            return acHash
        }
    }


}