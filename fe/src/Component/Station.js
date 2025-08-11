import axios from "axios";
import React, { useState } from "react";
import { useEffect } from "react";

const Station = ()=>{
    

    const [eta, setEta] = useState([]);
    const [people, setPeople] = useState([]);
    

    // axios 에러 때문에 일단 주석 처리하고 정적 값으로 스타일 조정~~!! 

    // useEffect(()=>{
    //     axios('/suttle/')
    //         .then(res => {
    //             setEta(res.data.time);
    //             setPeople(res.data.ppl);
    //             // time, ppl은 임시 key 값임 실제 key명 정해지면 수정할 것 (2025.08.11)
    //         })
    // })

    return(
        
        <div style={{padding:"3%"}}>
            <div>
                <div style={{display: "inline-block", backgroundColor: "purple", width: "20px", height:"20px", textAlign: "center", borderRadius: "50%", lineHeight: "20px", fontSize: "12px", color:"white"}}>5</div>
                <div style={{display: "inline-block", marginLeft: "5px", fontSize:"12px", fontWeight:"bold"}}>마장역</div>
           </div>
             
            <div style={{marginLeft: "38px"}}>
                <div style={{display:"inline-block", fontSize:"11px", marginRight:"7px"}}>8분</div>
                <div style={{display:"inline-block", fontSize:"9px", border: "0.5px solid lightgreen", padding: "1px 2px 1px 2px", borderRadius:"15%", color:"lightgreen"}}>여유</div>
            </div>
            

        </div>
        
            
        // <div style={style}>Station.js
        //     {eta.map(v => <div>{v.title}</div>)}
        //     <br/>
        //     {people.map(v => <div>{v.title}</div>)}
        // </div>
    ); 
    
}

export default Station;