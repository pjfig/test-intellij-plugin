package com.github.pjfig.testintellijplugin.toolWindow

import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.intellij.openapi.wm.ToolWindow
import com.intellij.openapi.wm.ToolWindowFactory
import com.intellij.ui.components.JBLabel
import com.intellij.ui.components.JBPanel
import com.intellij.ui.content.ContentFactory
import com.github.pjfig.testintellijplugin.MyBundle
import com.github.pjfig.testintellijplugin.services.MyProjectService
import com.intellij.openapi.ui.DialogPanel
import com.intellij.ui.dsl.builder.panel
import javax.swing.JButton


class MyToolWindowFactory : ToolWindowFactory {

    init {
        thisLogger().warn("Don't forget to remove all non-needed sample code files with their corresponding registration entries in `plugin.xml`.")
    }

    override fun createToolWindowContent(project: Project, toolWindow: ToolWindow) {
        val myToolWindow = MyToolWindow(toolWindow)
        //val content = ContentFactory.getInstance().createContent(myToolWindow.getContent(), null, false)
        val content = ContentFactory.getInstance().createContent(myToolWindow.newContent(), null, false)
        toolWindow.contentManager.addContent(content)
    }

    override fun shouldBeAvailable(project: Project) = true

    class MyToolWindow(toolWindow: ToolWindow) {

        private val service = toolWindow.project.service<MyProjectService>()

        fun newContent(): DialogPanel {
            val panel = panel {
                row {
                    label(MyBundle.message("rmiHostLabel"))
                    textField()
                }
                row {
                    label(MyBundle.message("rmiPostLabel"))
                    textField()

                }
                row{
                    label(MyBundle.message("loginLabel"))
                    textField()
                }
                row{
                    label(MyBundle.message("passwordLabel"))
                    textField()
                }
            }
            return panel;
        }

        fun getContent() = JBPanel<JBPanel<*>>().apply {
            val label = JBLabel(MyBundle.message("randomLabel", "?"))

            add(label)
            add(JButton(MyBundle.message("shuffle")).apply {
                addActionListener {
                    label.text = MyBundle.message("randomLabel", service.getRandomNumber())
                }
            })
            add(JBLabel(MyBundle.message("rmiHostLabel")))
            add(JBLabel(MyBundle.message("rmiPostLabel")))
            add(JBLabel(MyBundle.message("loginLabel")))
            add(JBLabel(MyBundle.message("passwordLabel")))

            //add(JBLabel(MyBundle.message("")))
        }
    }
}
