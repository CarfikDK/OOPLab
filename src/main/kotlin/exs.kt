abstract class Employee (
    private val firstName: String,
    private val secondName: String,
    private val baseSalary: Double,
    private val experience: Int,
    var manager: Manager ? = null
    ) {
    open fun calcSalary () : Double {
        var salary: Double = if (experience > 5) {
            baseSalary*1.2 + 500
        } else if (experience > 2) {
            baseSalary + 200
        } else {
            baseSalary
        }
        return salary;
    }

    fun giveSalary () {
        println("$firstName $secondName got salary: ${ Math.round(calcSalary()).toDouble() }")
    }

    fun assignToManager(assignedManager : Manager) {
        manager = assignedManager
        assignedManager.team.add(this)
    }
}

class Developer (
    firstName: String,
    secondName: String,
    baseSalary: Double,
    experience: Int
) : Employee (firstName, secondName, baseSalary, experience)  {

}

class Designer (
    firstName: String,
    lastName: String,
    baseSalary: Double,
    experience: Int,
    private var effCoeff : Double
) : Employee (firstName, lastName, baseSalary, experience)  {
    override fun calcSalary() : Double {
        return super.calcSalary() * effCoeff
    }
}



class Manager (
    firstName: String,
    lastName: String,
    baseSalary: Double,
    experience: Int,
    var team : MutableList<Employee> = mutableListOf()
) : Employee (firstName, lastName, baseSalary, experience)  {
    override fun calcSalary() : Double {
        var salary = super.calcSalary()
        val teamNum = team.count()
        val devNum = team.filterIsInstance<Developer>().count()
        if (teamNum > 10) {
            salary += 300
        } else if (teamNum > 5) {
            salary += 200
        }
        if (devNum > teamNum / 2) {
            salary *= 1.1
        }
        return salary;
    }

    fun addEmployee (employee : Employee) {
        team.add(employee)
        employee.manager = this
    }
}



class Department (
    var managers : MutableList<Manager> = mutableListOf()
) {

    fun addManager(manager: Manager) {
        managers.add(manager)
    }

    fun giveAllSalaries() {
        managers.forEach{
                manager ->
            manager.giveSalary()
            manager.team.forEach{
                employee ->  employee.giveSalary()
            }
        }
    }
}



fun main () {
    val jon =  Developer("Jon","Dou",1400.00, 1);
    val petya =  Developer("Petya","Vasiechkin",1500.00, 3)
    val vera =  Developer("Vera","Ivanova",1500.00, 5);
    val vasya =  Developer("Vasya", "Pupkin",1500.00, 2);
    val pablo =  Developer("Pablo","Neizvesten",1400.00, 4);
    val dima =  Developer("Dima","Sergeev",1500.00, 7);
    val tanya =  Developer("Tanya","Petrova",1500.00, 4);

    val katia =  Designer("Katia","Petrova",1400.00, 1, 0.7);
    val mila =  Designer("Mila","Yovovich",1400.00, 2, 0.6);
    val daria =  Designer("Daria","Kovalenko",1400.00, 6, 0.9);
    val danya =  Designer("Danya","Molchanov",1400.00, 4, 0.8);

    val platon =  Manager("Platon","Nachalnik",2000.00, 4);
    val borys =  Manager("Borys","Nachalnik",2000.00, 6);

    vasya.assignToManager(borys)
    petya.assignToManager(borys)
    daria.assignToManager(borys)
    katia.assignToManager(borys)
    dima.assignToManager(borys)
    jon.assignToManager(borys)
    vera.assignToManager(platon)

    pablo.assignToManager(platon)
    tanya.assignToManager(platon)
    danya.assignToManager(platon)
    mila.assignToManager(platon)

    val production = Department()

    production.addManager(borys)
    production.addManager(platon)

    production.giveAllSalaries()

}