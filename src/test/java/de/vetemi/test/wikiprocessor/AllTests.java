package de.vetemi.test.wikiprocessor;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({ ProcessorTest.class, TextProcessorTest.class, TokenizerTest.class, WikiIOTest.class })
public class AllTests {

}
