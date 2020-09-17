package uk.gov.justice.hmpps.architecture

import com.structurizr.model.Container
import com.structurizr.model.Model
import com.structurizr.model.Person
import com.structurizr.model.SoftwareSystem
import com.structurizr.view.ViewSet

class OffenderManagementInCustody private constructor() {
  companion object : HMPPSSoftwareSystem {
    lateinit var system: SoftwareSystem
    lateinit var ldu: Person
    lateinit var allocationManager: Container

    override fun defineModelEntities(model: Model) {
      system = model.addSoftwareSystem(
        "Offender Management in Custody",
        "A service for handling the handover of service users from prison to probation"
      )

      ldu = model.addPerson("Local Divisional Unit")

      allocationManager = system.addContainer("Offender Management Allocation Manager", "A service for allocating Prisoners to Prisoner Offender Managers (POMs)", "Ruby on Rails").apply {
        setUrl("https://github.com/ministryofjustice/offender-management-allocation-manager")
      }
    }

    override fun defineRelationships() {
      ldu.uses(allocationManager, "gets notification about ??? from", "gov.uk notify")
      ldu.uses(Delius.system, "maintains 'shadow' team assignments for service users during prison-to-probation handover in")
      allocationManager.uses(NOMIS.prisonApi, "polls service users currently in the logged in user's prison from")
    }

    override fun defineViews(views: ViewSet) {
      views.createContainerView(system, "omic-container", null).apply {
        addDefaultElements()
        add(Delius.system)
        add(NOMIS.prisonApi)

        setExternalSoftwareSystemBoundariesVisible(true)
        enableAutomaticLayout()
      }
    }
  }
}
