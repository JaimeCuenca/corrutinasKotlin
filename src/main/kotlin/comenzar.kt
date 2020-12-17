import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex

val hamaca = Mutex()
val hacha = Mutex()

const val CUBOS_NECESARIOS = 4
const val LENA_NECESARIA  = 2
const val RAMA_NECESARIA  = 1
const val COMIDA_NECESARIA  = 1

fun comenzar(){

    var cubosActuales = 0
    var lenaActual = 0
    var ramasActuales = 0
    var comidaActual = 0
    var ramas = false
    var comida = false
    var cubos = false
    var lena = false

    println(" ___________________________\n" +
            "|LLEGAMOS A LA ISLA DESIERTA|" +
            "\n ---------------------------")


    GlobalScope.launch {
        while (!cubos) {
            if (cubosActuales == CUBOS_NECESARIOS){
                println("CUBOS RECOLECTADOS")
                cubos = true
                irse(ramas, comida, cubos, lena)
            }else {
                labor(4000, 'A', "un cubo de agua")
                cubosActuales++
                descansar(1000, 'A')
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
                cogerHacha('B')
                labor(5000, 'B', "leña")
                dejarHacha('B')
                lenaActual++
                descansar(3000, 'B')
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
                labor(3000, 'C', "ramas")
                ramasActuales++
            }
            if (comidaActual == COMIDA_NECESARIA) {
                println("COMIDA CAZADA")
                comida = true
                irse(ramas, comida, cubos, lena)
            } else {
                cogerHacha('C')
                labor(4000, 'C', "comida")
                dejarHacha('C')
                comidaActual++
            }
        }
    }
}

suspend fun labor(tiempo: Long, nombre: Char, objeto: String){
    println("El amigo $nombre va a por $objeto")
    delay(tiempo)
    println("El amigo $nombre trae $objeto")
}

suspend fun cogerHacha(nombre: Char){
    hacha.lock()
    println("El amigo $nombre coge el hacha")

}

fun dejarHacha(nombre: Char){
    hacha.unlock()
    println("El amigo $nombre deja el hacha")
}

suspend fun descansar(tiempo: Long, nombre: Char) {
    println("El amigo $nombre quiere descansar")
    hamaca.lock()
    println("El amigo $nombre se tumba en la hamaca")
    delay(tiempo)
    println("El amigo $nombre se levanta de la hamaca")
    hamaca.unlock()
    println("El amigo $nombre ha descansado")
}

fun irse(ramas : Boolean, comida: Boolean, cubos: Boolean, lena: Boolean) {
    if(ramas && comida && cubos && lena)
        println("BARCA CONSTRUIDA Y APROVISIONADA CON ÉXITO")
}
