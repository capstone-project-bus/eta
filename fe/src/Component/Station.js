import axios from "axios";
import React, { useState } from "react";
import { useEffect } from "react";


const statusMap = {
  0: { text: "여유", color: "lightgreen" },
  1: { text: "보통", color: "yellow" },
  2: { text: "혼잡", color: "red" }
};

const Station = ()=>{
    

    const [state, setState] = useState([]);
    
    useEffect(() => {
    axios.get("http://localhost:8080/bus/1/count")
      .then(res => {
        setState(res.data.count);
      })
      .catch(err => console.error(err));
  }, []);

  const status = statusMap[state] ?? { text: "-", color: "gray" };

  
    
// 기준 폰트(부모 기준) = clamp(12px ~ 14px ~ 16px)
const baseFont = "clamp(0.75rem, 1.2vw + 0.2rem, 1rem)";

    return(
        
        <div style={{padding:"3%", fontSize: baseFont }}>
            <div style={{ display: "flex", alignItems: "center", gap: "0.375rem" }}>
                <div style={{
    display: "inline-flex",
    alignItems: "center",
    justifyContent: "center",
    background: "purple",
    color: "white",
    fontSize: "0.75rem",
    borderRadius: "50%",
    width: "1.5em",      // 텍스트 크기 비례
    aspectRatio: "1 / 1" // 정사각형 유지
  }}>5</div>
                <div style={{display: "inline-block", marginLeft: "5px", fontSize: "clamp(0.8rem, 1.1vw + 0.3rem, 1rem)" , fontWeight:"bold"}}>마장역</div>
           </div>
             
            <div style={{display: "flex", alignItems: "center", gap: "0.5rem", marginLeft: "2.2rem", marginTop: "0.25rem"}}>
                <div style={{fontSize: "0.9em", opacity: 0.9}}>8분</div>
                <div style={{
        fontSize: "0.85em",
        color: status.color,
        border: "0.2px solid currentColor",
        padding: "0.15em 0.4em",
        borderRadius: "0.25em",
        lineHeight: 1.2
      }}>{status.text}</div>
            </div>
            

        </div>
        
        
    ); 
    
}

export default Station;