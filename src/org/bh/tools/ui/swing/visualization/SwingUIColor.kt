package org.bh.tools.ui.swing.visualization

import org.bh.tools.ui.visualization.*
import java.awt.*

/**
 * The Java Swing implementation of UIColor
 *
 * @author Ben Leggiero
 * @since 2018-07-01
 */
class SwingUIColor(nativeValue: Color) : UIColor<Color>(nativeValue)



/**
 * The Java Swing System Color implementation of UIColor
 *
 * @author Ben Leggiero
 * @since 2018-07-01
 */
class SwingSystemUIColor(nativeValue: SystemColor) : UIColor<SystemColor>(nativeValue)
