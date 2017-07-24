package org.bh.tools.ui.fx

import javafx.stage.Stage
import org.bh.tools.base.struct.*

/**
 * Husky UI's wrapper around a JavaFX Stage
 *
 * @author Ben Leggiero
 * @since 2017-07-19
 */
class FXWindow<ContentViewController>(var javaFxValue: Stage): UIWindow<ContentViewController> where ContentViewController: UIViewController<*> {
    override lateinit var contentViewController: ContentViewController

    override fun present() {
        javaFxValue.show()
    }
}