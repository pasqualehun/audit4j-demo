package org.audit4j.validation.jgiven;

import java.io.IOException;

import org.audit4j.validation.jgiven.enumeration.Version;
import org.audit4j.validation.jgiven.util.TesterUtil;
import org.audit4j.validation.jgiven.stage.GivenSomeState;
import org.audit4j.validation.jgiven.stage.ThenSomeOutcome;
import org.audit4j.validation.jgiven.stage.WhenSomeAction;
import org.junit.Rule;
import org.junit.Test;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.rules.TemporaryFolder;

import com.tngtech.jgiven.junit.ScenarioTest;

public class CombinedConsoleAndFileAuditHandlerTest 
	extends ScenarioTest<GivenSomeState, WhenSomeAction, ThenSomeOutcome> {
	
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
    
	@Rule
	public TemporaryFolder folder = new TemporaryFolder();
	
	
    private String getFilePath() {
    	return folder.getRoot().getAbsolutePath() +"\\"+ TesterUtil.getAuditFileName();
    }
	
	
	
	@Test
	public void combined_console_and_file_audit_handler_with_programming_configuration_receiving_a_message_with_event_builder() throws IOException {
		
		given().audit4j_starting_with_a_programming_configuration_using_ConsoleAuditHandler_and_FileAuditHandler(folder);
        when().a_message_is_sent_to_the_audit_manager_with_event_builder()
        .and().audit_manager_is_stopped();
        then().the_console_log_contains_the_audit4j_logo_with_version_$(systemOutRule, Version.V250) 
        .and().the_console_log_contains_the_expected_output_for_event_builder(systemOutRule)
        .and().the_file_log_contains_$_line(1, getFilePath())
        .and().the_file_log_contains_the_expected_output_for_event_builder(getFilePath());
        

	}

	@Test
	public void combined_console_and_file_audit_handler_with_programming_configuration_receiving_a_message_with_audit_event() throws IOException {
		
		given().audit4j_starting_with_a_programming_configuration_using_ConsoleAuditHandler_and_FileAuditHandler(folder);
        when().a_message_is_sent_to_the_audit_manager_with_audit_event()
        .and().audit_manager_is_stopped();
        then().the_console_log_contains_the_audit4j_logo_with_version_$(systemOutRule, Version.V250) 
        .and().the_console_log_contains_the_expected_output_for_audit_event(systemOutRule)
        .and().the_file_log_contains_$_line(1, getFilePath())
        .and().the_file_log_contains_the_expected_output_for_audit_event(getFilePath());
       		
	}
	
	@Test
	public void combined_console_and_file_audit_handler_with_programming_configuration_receiving_a_message_with_event_builder_with_pause() throws IOException {
		
		given().audit4j_starting_with_a_programming_configuration_using_ConsoleAuditHandler_and_FileAuditHandler(folder);
        when().audit_manager_is_disabled()
        .and().a_message_is_sent_to_the_audit_manager_with_event_builder()
        .and().audit_manager_is_stopped();
        then().the_console_log_contains_the_audit4j_logo_with_version_$(systemOutRule, Version.V250) 
        .but().the_console_log_does_not_contains_messages_for_event_builder(systemOutRule)
        .and().the_file_log_contains_$_line(0, getFilePath());
        

		
	}
}
