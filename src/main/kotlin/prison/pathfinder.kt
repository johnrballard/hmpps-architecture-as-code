package uk.gov.justice.hmpps.architecture.prison

import com.structurizr.model.Container
import com.structurizr.model.Model
import com.structurizr.model.SoftwareSystem

class Pathfinder(model: Model) {
    val system: SoftwareSystem
    val webApp: Container

    init {
        val pathfinder = model.addSoftwareSystem("Pathfinder", """
    Pathfinder System,
    the case management system for Pathfinder nominals
    """.trimIndent())

        val db = pathfinder.addContainer("Pathfinder Database", "Database to store Pathfinder case management", "RDS Postgres DB").apply {
            addTags("database")
        }

        webApp = pathfinder.addContainer("Pathfinder Web Application", "Web application for the case management of Pathfinder nominals", "Node Express app").apply {
            addTags("WebBrowser")
            uses(db, null)
            uses(model.getSoftwareSystemWithName("NOMIS")!!.getContainerWithName("Elite2API")!!, "extract NOMIS offender data")
            uses(model.getSoftwareSystemWithName("NOMIS")!!.getContainerWithName("PrisonerSearch")!!, "to search for prisoners")
            uses(model.getSoftwareSystemWithName("nDelius")!!.getContainerWithName("CommunityAPI")!!, "extract nDelius offender data")
            uses(model.getSoftwareSystemWithName("nDelius")!!.getContainerWithName("OffenderSearch")!!, "to search for offenders")
        }

        pathfinder.addContainer("Pathfinder API", "API over the Pathfinder DB used by internal applications", "Kotlin Spring Boot App").apply {
            setUrl("https://github.com/ministryofjustice/pathfinder-api")
            uses(db, "JDBC")
        }

        val hmppsAuth: SoftwareSystem = model.getSoftwareSystemWithName("HMPPS Auth")!!
        hmppsAuth.addTags("")

        val pPrisonPreventLead = model.addPerson("Prison Prevent Lead", "They case manage Pathfinder Nominals in custody")
        pPrisonPreventLead.uses(webApp, "Visits pathfinder.service.justice.gov.uk using", "HTTPS")
        pPrisonPreventLead.uses(hmppsAuth, "Login")

        val pRegionalPrisonPreventLead = model.addPerson("Regional Prison Prevent Lead", "They case manage a region of Pathfinder Nominals in custody")
        pRegionalPrisonPreventLead.uses(webApp, "Visits pathfinder.service.justice.gov.uk using", "HTTPS")
        pRegionalPrisonPreventLead.uses(hmppsAuth, "Login")

        val pProbationOffenderManager = model.addPerson("Probation Offender Manager", "They case manage Pathfinder Nominals in the community")
        pProbationOffenderManager.uses(webApp, "Visits pathfinder.service.justice.gov.uk using", "HTTPS")
        pProbationOffenderManager.uses(hmppsAuth, "Login")

        val pRegionalPoliceUser = model.addPerson("Regional Police User", "They access limited regional data of Pathfinder Nominals")
        pRegionalPoliceUser.uses(webApp, "Visits pathfinder.service.justice.gov.uk using", "HTTPS")
        pRegionalPoliceUser.uses(hmppsAuth, "Login")

        val pNationalPoliceUser = model.addPerson("National Police User", "They access limited National data of Pathfinder Nominals")
        pNationalPoliceUser.uses(webApp, "Visits pathfinder.service.justice.gov.uk using", "HTTPS")
        pNationalPoliceUser.uses(hmppsAuth, "Login")

        val pNationalIntelligenceUnitUser = model.addPerson("National Prison User", "They have access to all Pathfinder nominals")
        pNationalIntelligenceUnitUser.uses(webApp, "Visits pathfinder.service.justice.gov.uk using", "HTTPS")
        pNationalIntelligenceUnitUser.uses(hmppsAuth, "Login")

        system = pathfinder
    }
}
