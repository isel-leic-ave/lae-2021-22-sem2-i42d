package pt.isel.sample

import java.io.Closeable
import java.io.FileOutputStream
import java.lang.ref.Cleaner
import java.lang.ref.Cleaner.Cleanable

class FilePrinterCleanable(val out: FileOutputStream, val buffer: StringBuilder) : Runnable {
    override fun run() {
        if(buffer.isNotEmpty()) {
            out.write(buffer.toString().toByteArray())
        }
        out.close()
    }
}
private val cleaner = Cleaner.create()

/**
 * What to do when we have a Closeable class???
 * R: We have to implement Closeable too and override close()
 *
 * NOTICE this is a naif approach. StringBuilder is not the most effective buffer.
 */
class FilePrinter(path: String) : Closeable {
    private val threshold = 16
    private val out = FileOutputStream(path)
    private val buffer = StringBuilder()
    private val register: Cleanable = cleaner.register(this, FilePrinterCleanable(out, buffer))

    fun print(msg: String) {
        buffer.append(msg)
        if(buffer.length > threshold) {
            out.write(buffer.toString().toByteArray())
            buffer.clear()
        }
    }
    /**
     * CALL cleanup code via register that will remove the
     * FilePrinterClenable object from the cleaner.
     * Otherwise, you may get the cleanup called twice!!!
     */
    override fun close() {
        register.clean()
    }
    /**
     * We call the close() of all properties Objects that are Closeable.
     * !!!! ATTENTION control execution with a flag.
     * This function can be called more than once!
     */
    /*
    override fun close() {
        if(buffer.isNotEmpty()) {
            // May throw an Exception if the out stream is already closed.
            //
            out.write(buffer.toString().toByteArray())
        }
        out.close()
    }
    protected fun finalize() {
        println("# Finalizing... #")
        this.close()
    }
    */
}

fun main() {
    demo4()
}

fun demo4() {
    FilePrinter("out.txt").print("LAE course: ")

    println("Before finalization ")
    readLine()

    System.gc()
    System.runFinalization()
    println("After GC and finalization ")
    readLine()
}


fun demo3() {
    FilePrinter("out.txt").use {
        it.print("LAE course: ")
        readLine()
    } // implicit it.close()
}


fun demo2() {
    FilePrinter("out.txt").print("LAE course: ")
}


fun demo1() {
    val fo = FilePrinter("out.txt")
    fo.print("LAE course: ")

    println("Buffer is not full and out.txt is still empty")
    readLine()

    fo.print("lesson 35 closeable and cleaners!")

    println("Buffer was flush and out.txt has text")
    readLine()

}
