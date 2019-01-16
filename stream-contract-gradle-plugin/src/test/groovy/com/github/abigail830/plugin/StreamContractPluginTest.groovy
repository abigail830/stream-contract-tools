package com.github.abigail830.plugin;

import org.gradle.api.Project;
import org.gradle.testfixtures.ProjectBuilder
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

@Ignore
class StreamContractPluginTest {

    @Test
    void testTaskTypeMatch() {
        Project project = ProjectBuilder.builder().build()
        project.pluginManager.apply 'com.github.abigail830.stream-contract-gradle-plugin-groovy'

        assertTrue(project.tasks.cleanContract instanceof CleanContractTask)
    }

//    @Test
//    void apply() {
//        Project project = ProjectBuilder.builder().build()
//        project.pluginManager.apply 'com.github.abigail830.stream-contract-gradle-plugin'
//        project.property('config').s
//
//        assertTrue(project.tasks.cleanContract instanceof CleanContractTask)
//    }
}