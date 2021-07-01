package uk.gov.justice.digital.hmpps.courthearingeventreceiver.controller

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import uk.gov.justice.digital.hmpps.courthearingeventreceiver.service.MessageNotifier

@Api(value = "Event")
@RestController
class EventController(
  @Autowired
  private val messageNotifier: MessageNotifier
) {

  @ApiOperation(value = "Endpoint to receive events")
  @RequestMapping(value = ["/event"], method = [RequestMethod.POST], produces = [MediaType.APPLICATION_JSON_VALUE])
  @ResponseStatus(HttpStatus.ACCEPTED)
  fun postEvent(@RequestBody body: String) {
    log.info("Received event payload: %s".format(body))
    messageNotifier.send(body)
  }

  companion object {
    private val log = LoggerFactory.getLogger(this::class.java)
  }
}