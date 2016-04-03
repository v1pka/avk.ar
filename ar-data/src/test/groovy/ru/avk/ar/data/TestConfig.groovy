package ru.avk.ar.data

import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import
import ru.avk.BaseDataConfig

/**
 * Created by ipopkov on 02/04/16.
 */
@Configuration
@Import([BaseDataConfig.class])
class TestConfig {

}