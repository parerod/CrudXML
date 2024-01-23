

import org.simpleframework.xml.Attribute
import org.simpleframework.xml.Element
import org.simpleframework.xml.ElementList
import org.simpleframework.xml.Root

@Root(name = "rutas")
data class Rutas(
    @field:ElementList(inline = true, entry = "ruta")
    var rutas: List<Ruta> = mutableListOf()
)

@Root(name = "ruta")
data class Ruta(
    @field:Element(name = "nombre")
    var nombre: String = "",

    @field:Element(name = "x")
    var x: Int = 0,

    @field:Element(name = "y")
    var y: Int = 0
)
