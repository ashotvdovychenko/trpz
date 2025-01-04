package org.example.service;

import org.yaml.snakeyaml.Yaml;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Map;

import static org.example.service.utility.ServiceConstants.WORKFLOW_FILENAME;

public class YamlWorkflowProcessor {
    private static YamlWorkflowProcessor instance;

    private YamlWorkflowProcessor() {
    }

    public static YamlWorkflowProcessor getInstance() {
        if (instance == null) {
            synchronized (YamlWorkflowProcessor.class) {
                if (instance == null) {
                    instance = new YamlWorkflowProcessor();
                }
            }
        }
        return instance;
    }

    public Map<String, Object> extractWorkflowData(String projectDir) {
        try (var yamlFile = new FileInputStream(Path.of(projectDir, WORKFLOW_FILENAME).toString())) {
            Yaml yaml = new Yaml();
            return yaml.load(yamlFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getCommandFromWorkflowData(Map<String, Object> workflowData) {
        return workflowData.get("run").toString();
    }

    public Map<String, String> getWithVariablesFromWorkflowData(Map<String, Object> workflowData) {
        return (Map<String, String>) workflowData.get("with");
    }
}
