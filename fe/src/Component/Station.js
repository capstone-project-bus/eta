import React from "react";

const statusMap = {
  0: { text: "여유", color: "lightgreen" },
  1: { text: "보통", color: "yellow" },
  2: { text: "혼잡", color: "red" }
};

const Station = ({ stations = [], eta = {}, count }) => {
  const status = statusMap[count] ?? { text: "-", color: "gray" };

  const baseFont = "clamp(0.75rem, 1.2vw + 0.2rem, 1rem)";

  return (
    <div style={{ padding: "3%", fontSize: baseFont }}>
      {stations.map((s, idx) => {
        const etaValue = eta[idx];
        const isWangsimni = s.text === "왕십리역"; // 왕십리만 marginBottom 크게

        return (
          <div key={idx} style={{ marginBottom: isWangsimni ? "3rem" : "0.5rem" }}>
            {/* 아이콘 + 역 이름 */}
            <div style={{ display: "flex", alignItems: "center", gap: "0.375rem" }}>
              <div
                style={{
                  display: "inline-flex",
                  alignItems: "center",
                  justifyContent: "center",
                  background: s.color,
                  color: "white",
                  fontSize: "0.75rem",
                  borderRadius: "50%",
                  width: "1.5em",
                  aspectRatio: "1 / 1"
                }}
              >
                {s.line}
              </div>
              <div
                style={{
                  display: "inline-block",
                  marginLeft: "5px",
                  // fontSize: "clamp(0.8rem, 1.1vw + 0.3rem, 1rem)",
                  fontSize: "15px",
                  fontWeight: "bold"
                }}
              >
                {s.text}
              </div>
            </div>

            {/* ETA + 상태 */}
            <div
              style={{
                display: "flex",
                alignItems: "center",
                gap: "0.5rem",
                marginLeft: "2.2rem",
                marginTop: "0.25rem"
              }}
            >
              {/* <div style={{ fontSize: "0.9em", opacity: 0.9 }}> */}
              <div style={{ fontSize: "15px", color: "#f41a1a", fontWeight: "bold" }}>
                {etaValue || " "}
              </div>
              <div
                style={{
                  fontSize: "0.85em",
                  color: status.color,
                  border: "0.2px solid currentColor",
                  padding: "0.15em 0.4em",
                  borderRadius: "0.25em",
                  lineHeight: 1.2,
                  minWidth: "2em"
                }}
              >
                {status.text}
              </div>
            </div>
          </div>
        );
      })}
    </div>
  );
};

export default Station;
