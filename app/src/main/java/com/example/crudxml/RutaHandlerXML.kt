package com.example.crudxml


import Ruta
import org.xml.sax.Attributes
import org.xml.sax.SAXException
import org.xml.sax.helpers.DefaultHandler

class RutaHandlerXML : DefaultHandler() {

    private val cadena = StringBuilder()
    private var ruta: Ruta? = null
    var rutas: MutableList<Ruta> = mutableListOf()

    @Throws(SAXException::class)
    override fun startDocument() {
        cadena.clear()
        rutas = mutableListOf()
        println("startDocument")
    }

    @Throws(SAXException::class)
    override fun startElement(uri: String, nombreLocal: String, nombre: String, attributes: Attributes) {
        cadena.setLength(0)
        if (nombre == "ruta") {
            ruta = Ruta()
        }

        println("startElement: $nombreLocal $nombre")
    }

    @Throws(SAXException::class)
    override fun characters(ch: CharArray, start: Int, length: Int) {
        cadena.append(ch, start, length)
        println("dato final: $cadena")
    }

    @Throws(SAXException::class)
    override fun endElement(uri: String, nombreLocal: String, nombre: String) {
        when (nombre) {
            "nombre" -> ruta?.nombre = cadena.toString()
            "horasLectivas" -> ruta?.x = cadena.toString().toInt()
            "mayor55" -> ruta?.y = cadena.toString().toInt()
        }

        println("endElement: $nombreLocal $nombre")
    }

    @Throws(SAXException::class)
    override fun endDocument() {
        println("endDocument")
    }
}