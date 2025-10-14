package com.karthik.pro.engr.algocompose.ui.viewmodel.stay

sealed class BudgetStayEvent {
    data class AddBudget(val budget: String) : BudgetStayEvent()
    data class AddHotelPrice(val price: String) : BudgetStayEvent()
    object ComputeBudgetStay : BudgetStayEvent()
    object Reset : BudgetStayEvent()
}