package org.bh.tools.ui.swing

import org.bh.tools.base.collections.extensions.*
import org.bh.tools.ui.alert.*
import org.bh.tools.ui.alert.HuskyOptionPaneMessageType.*
import org.bh.tools.ui.alert.HuskyOptionPaneOptionType.*
import javax.swing.*

/**
 * @author Ben
 * @since 2018-04-30
 */

object SwingHuskyUserPrompt: HuskyUserPrompt<JComponent> {
    override fun showOptionDialog(
            parentComponent: JComponent?,
            title: String,
            message: String,
            messageType: HuskyOptionPaneMessageType,
            optionType: HuskyOptionPaneOptionType,
            allOptions: List<AlertOption>,
            defaultOption: AlertOption?,
            onUserSelection: (selectedOption: AlertOption) -> Unit
    ) {
        val selectedOptionIndex = JOptionPane.showOptionDialog(
                parentComponent,
                message,
                title,
                optionType.swingValue,
                messageType.swingValue,
                null,
                (allOptions.nonEmpty ?: optionType.defaultAlertOptions).toTypedArray(),
                defaultOption
        )

        val selectedOption = if (selectedOptionIndex < 0) AlertOption.close else allOptions[selectedOptionIndex]
        onUserSelection(selectedOption)
    }
}
val HuskyOptionPaneOptionType.swingValue: Int get() = when (this) {
    default -> JOptionPane.DEFAULT_OPTION
    yesNo -> JOptionPane.YES_NO_OPTION
    yesNoCancel -> JOptionPane.YES_NO_CANCEL_OPTION
    okCancel -> JOptionPane.OK_CANCEL_OPTION
    ok -> JOptionPane.OK_CANCEL_OPTION
}
val HuskyOptionPaneMessageType.swingValue: Int get() = when (this) {
    plain -> JOptionPane.PLAIN_MESSAGE
    info -> JOptionPane.INFORMATION_MESSAGE
    question -> JOptionPane.QUESTION_MESSAGE
    warning -> JOptionPane.WARNING_MESSAGE
    error -> JOptionPane.ERROR_MESSAGE
}