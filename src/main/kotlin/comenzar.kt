import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex

val hamaca = Mutex()
val hacha = Mutex()

fun comenzar(){

    var cubosActuales = 0
    var lenaActual = 0
    var ramasActuales = 0
    var comidaActual = 0
    var ramas = false
    var comida = false
    var cubos = false
    var lena = false

    val CUBOS_NECESARIOS = 4
    val LENA_NECESARIA  = 2
    val RAMA_NECESARIA  = 1
    val COMIDA_NECESARIA  = 1

    println(" ___________________________\n|LLEGAMOS A LA ISLA DESIERTA|\n ---------------------------");


    GlobalScope.launch {
        while (!cubos) {
            if (cubosActuales == CUBOS_NECESARIOS){
                println("CUBOS RECOLECTADOS")
                cubos = true
                irse(ramas, comida, cubos, lena)
            }else {
                println("El amigo A va a por agua.")
                delay(4000)
                println("El amigo A trae agua.")
                cubosActuales++
                println("El amigo A quiere descansar")
                descansar(1000, 'A')
                println("El amigo A ha descansado")
            }
        }
    }

    GlobalScope.launch {
        while (!lena) {
            if(lenaActual == LENA_NECESARIA){
                println("LEÑA RECOLECTADA")
                lena = true
                irse(ramas, comida, cubos, lena)
            }else {
                println("El amigo B va a por lena.")
                cogerHacha()
                println("El amigo B coge el hacha.")
                delay(5000)
                println("El amigo B deja el hacha")
                dejarHacha()
                println("El amigo B vuelve con la lena")
                lenaActual++
                println("El amigo B quiere descansar")
                descansar(3000, 'B')
                println("El amigo B ha descansado")
            }
        }
    }
    GlobalScope.launch {
        while (!comida && !ramas) {
            if (ramasActuales == RAMA_NECESARIA) {
                println("REMOS TERMINADOS")
                ramas = true
                irse(ramas, comida, cubos, lena)
            } else {
                println("El amigo C va a por ramas.")
                delay(3000)
                println("El amigo C trae ramas.")
                ramasActuales++
            }
            if (comidaActual == COMIDA_NECESARIA) {
                println("COMIDA CAZADA")
                comida = true
                irse(ramas, comida, cubos, lena)
            } else {
                cogerHacha()
                println("El amigo C coge el hacha")
                println("El amigo C se va a cazar")
                delay(4000)
                println("El amigo C vuelve de cazar")
                dejarHacha()
                comidaActual++
            }
        }
    }
}

fun irse(ramas : Boolean, comida: Boolean, cubos: Boolean, lena: Boolean) {
    if(ramas && comida && cubos && lena){
        println("BARCA CONSTRUIDA Y APROVISIONADA CON ÉXITO")
    }
}

suspend fun cogerHacha(){
    hacha.lock()
}
fun dejarHacha(){
    hacha.unlock()
}

suspend fun descansar(tiempo: Long, nombre: Char) {
    hamaca.lock()
    println("El amigo "+nombre+" se tumba en la hamaca")
    delay(tiempo)
    println("El amigo "+nombre+" se levanta de la hamaca")
    hamaca.unlock()
}
