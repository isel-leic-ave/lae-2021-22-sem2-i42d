package pt.isel

class SavingsAccount(@ToLog var balance: Long, var annualInterestRate: Double) {

    fun deposit(amount: Long) { balance += amount }

    fun withdraw(amount: Long) { balance -= amount }

    @ToLog fun monthlyInterest(): Long {
        val monthlyInterestRate = annualInterestRate / 12
        return (balance * monthlyInterestRate).toLong()
    }

    /**
     * Calculates and adds the monthly interest to the balance
     */
    fun accrueMonthlyInterest() {
        balance += monthlyInterest()
    }
}
