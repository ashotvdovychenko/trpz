import React, { useState, useEffect, useRef } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";

const BuildLogsPage = () => {
    const { id, buildId } = useParams();
    const [logs, setLogs] = useState([]);
    const [status, setStatus] = useState("");
    const socketRef = useRef(null);

    useEffect(() => {
        axios.get(`http://localhost:8080/builds/${buildId}/logs`).then(response => setLogs(response.data.split("\n")));

        socketRef.current = new WebSocket(`ws://localhost:8080/project-builds/${buildId}`);
        socketRef.current.onmessage = event => {
            setLogs(prevLogs => [...prevLogs, event.data]);
        };

        socketRef.current.onopen = () => {
            axios.get(`http://localhost:8080/builds/${buildId}`).then(response => setStatus(response.data.buildStatus));
        };

        return () => socketRef.current.close();
    }, [id, buildId]);

    return (
        <div>
            <h1>Build Logs</h1>
            <div style={{ height: "300px", overflowY: "scroll", border: "1px solid black" }}>
                {logs.map((log, index) => (
                    <p key={index}>{log}</p>
                ))}
            </div>
        </div>
    );
};

export default BuildLogsPage;
