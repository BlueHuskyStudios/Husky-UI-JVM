package org.bh.tools.ui.fx

import javafx.fxml.FXMLLoader
import java.io.IOException
import javafx.scene.*
import javafx.stage.Stage
import org.bh.tools.base.struct.UIViewController
import org.bh.tools.base.struct.UIWindowController


/**
 * Controls a [FXWindow]
 *
 * @author Ben Leggiero
 * @since 2017-07-19
 */
open class FXWindowController<Window: FXWindow<UIViewController<*>>>(val fxmlLocation: String): UIWindowController<Window> {

    /**
     * The window that this controller controls. This is `null` before [loadWindow] runs.
     */
    override var window: Window? = null


    override fun loadWindow() {
        windowWillLoad()

        val root: Parent
        try {
            root = FXMLLoader.load<Parent>(javaClass.classLoader.getResource("path/to/other/view.fxml")!!)
            val stage = Stage()
            stage.title = "Window"
            stage.scene = Scene(root, 450.0, 450.0)
        } catch (e: IOException) {
            e.printStackTrace()
        } finally {
            windowDidLoad()
        }
    }


    override fun windowWillLoad() {
        // Do nothing
    }


    override fun windowDidLoad() {
        // Do nothing
    }
}
