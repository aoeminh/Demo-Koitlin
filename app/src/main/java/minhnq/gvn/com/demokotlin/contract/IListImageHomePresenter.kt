package minhnq.gvn.com.demokotlin.contract

interface IListImageHomePresenter {
    fun getListImageHome(categoryImage: Int, skip: Int, take: Int)
}