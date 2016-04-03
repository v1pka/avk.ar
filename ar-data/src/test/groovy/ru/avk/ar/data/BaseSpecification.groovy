package ru.avk.ar.data

import org.junit.runner.RunWith
import org.spockframework.runtime.Sputnik
import org.springframework.boot.test.SpringApplicationContextLoader
import org.springframework.test.context.ContextConfiguration
import spock.lang.Specification

@ContextConfiguration(loader = SpringApplicationContextLoader,
        classes = [TestConfig])
@RunWith(Sputnik)
abstract class BaseSpecification extends Specification {

}