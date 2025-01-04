import React, { useState, useEffect } from "react";
import { useParams, useNavigate } from "react-router-dom";
import axios from "axios";

const ProjectDetailsPage = () => {
    const { id } = useParams();
    const [branches, setBranches] = useState([]);
    const [selectedBranch, setSelectedBranch] = useState("");
    const [builds, setBuilds] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        axios.get(`http://localhost:8080/projects/${id}/branches`).then(response => setBranches(response.data));
        axios.get(`http://localhost:8080/projects/${id}/builds`).then(response => setBuilds(response.data));
    }, [id]);

    const createBuildAndStart = () => {
        axios.post(`http://localhost:8080/projects/${id}/builds`, null, { params: { branch: selectedBranch } }).then(response => {
            const buildId = response.data.id;
            const socket = new WebSocket(`ws://localhost:8080/project-builds/${buildId}`);
            socket.onopen = () => {
                socket.send("start");
                navigate(`/projects/${id}/builds/${buildId}`);
            };
        });
    };

    return (
        <div>
            <h1>Project Details</h1>
            <h2>Branches</h2>
            <select value={selectedBranch} onChange={e => setSelectedBranch(e.target.value)}>
                <option value="" disabled>
                    Select a branch
                </option>
                {branches.map(branch => (
                    <option key={branch} value={branch}>
                        {branch}
                    </option>
                ))}
            </select>
            <button onClick={createBuildAndStart} disabled={!selectedBranch}>Start Build</button>

            <h2>Builds</h2>
            <ul>
                {builds.map(build => (
                    <li key={build.id}>
                        <span>Build #{build.id} - {build.buildStatus}</span>
                        <button onClick={() => navigate(`/projects/${id}/builds/${build.id}`)}>View Logs</button>
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default ProjectDetailsPage;