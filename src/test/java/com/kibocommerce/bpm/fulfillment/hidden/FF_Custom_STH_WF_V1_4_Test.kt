package com.kibocommerce.bpm.fulfillment.hidden

import com.kibocommerce.bpm.fulfillment.assertCurrentState
import org.jbpm.test.JbpmJUnitBaseTestCase
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.kie.api.runtime.KieSession
import org.kie.api.runtime.process.WorkflowProcessInstance
import org.kie.api.task.TaskService
import org.kie.api.task.model.Status
import org.slf4j.LoggerFactory
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import javax.naming.InitialContext
import javax.transaction.UserTransaction

class FF_Custom_STH_WF_V1_4_Test : JbpmJUnitBaseTestCase(true, false) {
    private var kieSession: KieSession? = null
    private var taskService: TaskService? = null
    private lateinit var userTransaction: UserTransaction

    @Before
    fun init() {
        createRuntimeManager("com/kibocommerce/bpm/fulfillment/hidden/FF_Custom_STH_WF_V1.4.bpmn")
        val runtimeEngine = getRuntimeEngine(null)
        kieSession = runtimeEngine.kieSession
        taskService = runtimeEngine.taskService
        userTransaction = InitialContext.doLookup("java:comp/UserTransaction")
        userTransaction.begin()
    }

    @After
    fun cleanup() {
        try {
            userTransaction.commit()
        } catch (e: Exception) {
            try { userTransaction.rollback() } catch (ignored: Exception) {}
        }
        kieSession?.dispose() 
    }

    @Test
    fun acceptShipment_true() {
        val wpi = createProcess()
        
        acceptShipment(wpi, true)

        assertNodeActive(wpi.id, kieSession, "Validate Items In Stock")
        assertCurrentState(wpi, "ACCEPTED_SHIPMENT")
    }

    @Test
    fun acceptShipment_false() {
        val wpi = createProcess()
        
        acceptShipment(wpi, false)

        assertProcessInstanceNotActive(wpi.id, kieSession)
        assertCurrentState(wpi, "REASSIGN_SHIPMENT")
    }

    private fun createProcess(): WorkflowProcessInstance {
        val processParam = mapOf("initiator" to "john")
        val processInstance = kieSession!!.startProcess("FF_Custom_STH_WF_V1.4", processParam)

        assertTrue(processInstance is WorkflowProcessInstance)
        assertNodeExists(
            processInstance,
            "Accept Shipment",
            "Validate Items In Stock",
            "Print Packing Slip",
            "Prepare for Shipment"
        )
        assertNodeTriggered(processInstance.id, "Pre-Accept Shipment", "Accept Shipment")
        assertEquals("PRE_ACCEPT_SHIPMENT", processInstance.getVariable("currentState"))

        logger.info("Created process {}", processInstance.processName)
        return processInstance
    }

    private fun acceptShipment(wpi: WorkflowProcessInstance, shipmentAccepted: Boolean) {
        val expectedTaskName = "Accept Shipment"

        assertProcessInstanceActive(wpi.id, kieSession)
        assertNodeActive(wpi.id, kieSession, expectedTaskName)
        
        val tasks = taskService!!.getTasksByStatusByProcessInstanceId(wpi.id, listOf(Status.Reserved), "en-US")
        assertEquals(1, tasks.size)
        val task = tasks[0]
        assertEquals(expectedTaskName, task.name)
        taskService!!.start(task.id, "john")
        val data = mapOf("shipmentAccepted" to shipmentAccepted)
        taskService!!.complete(task.id, "john", data)
    }

    companion object {
        private val logger = LoggerFactory.getLogger(FF_Custom_STH_WF_V1_4_Test::class.java)
    }
}