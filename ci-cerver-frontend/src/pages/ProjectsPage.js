import React, { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

const ProjectsPage = () => {
    const [projects, setProjects] = useState([]);
    const [newProject, setNewProject] = useState({ name: "", repositoryUrl: "" });
    const navigate = useNavigate();

    useEffect(() => {
        axios.get("http://localhost:8080/projects").then(response => setProjects(response.data));
    }, []);

    const createProject = () => {
        axios.post("http://localhost:8080/projects", newProject).then(response => {
            setProjects([...projects, response.data]);
            setNewProject({ name: "", repositoryUrl: "" });
        });
    };

    return (
        <div>
            <h1>Projects</h1>
            <ul>
                {projects.map(project => (
                    <li key={project.id}>
                        <span>{project.name}</span>
                        <button onClick={() => navigate(`/projects/${project.id}`)}>View</button>
                    </li>
                ))}
            </ul>
            <h2>Create Project</h2>
            <input
                type="text"
                placeholder="Project Name"
                value={newProject.name}
                onChange={e => setNewProject({ ...newProject, name: e.target.value })}
            />
            <input
                type="text"
                placeholder="Repository URL"
                value={newProject.repositoryUrl}
                onChange={e => setNewProject({ ...newProject, repositoryUrl: e.target.value })}
            />
            <button onClick={createProject}>Create</button>
        </div>
    );
};

export default ProjectsPage;