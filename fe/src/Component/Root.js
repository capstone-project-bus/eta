import React, { useState, useEffect } from "react";
import Station from "./Station";
import RefreshButton from "./Refresh";  
import styles from '../css/RootCss.module.css';
import axios from "axios";
import Ai from './Ai';


const Root = () => {
  const [toggleState, setToggleState] = useState(1);
  const [count, setCount] = useState(null); // Station 데이터 가져옴 

  // 데이터 패칭 함수
  const fetchData = async () => {
    try {
      const res = await axios.get("http://localhost:8080/bus/1/count");
      setCount(res.data.count);
    } catch (err) {
      console.error(err);
    }
  };

  // 페이지 로드 시 데이터 불러오기
  useEffect(() => {
    fetchData();
  }, []);

  const stationMap = {
    0: { text: "마장역", color: "#A05EB5", line: "5" },
    1: { text: "한양대역", color: "#00B140", line: "2" },
    2: { text: "왕십리역", color:"#77C4A3", line: "" },
    3: { text: "학교", color:"yellow", line: "" },
    4: { text: "", color: "", line: ""}
  };

  const stationGroups = {
    1: [0, 1, 3], //한/마
    2: [2, 3] // 왕 
  };

  // eta 임시 demo ---------------------------------------------------------
  const stationEtas = {
  0: "3분",
  1: "8분",
  2: "12분",
  3: null,   // 아직 도착정보 없음
  4: ""      // 공백 테스트~~ 
};


  const stationToShow = stationGroups[toggleState].map(id => stationMap[id]);

  return (
    <div>
      <div className={styles.blocTabs}>
        <div
          className={toggleState === 1 ? `${styles.tabs} ${styles.activeTabs}` : styles.tabs}
          onClick={() => setToggleState(1)}
        >
          한양대/마장 노선
        </div>

        <div
          className={toggleState === 2 ? `${styles.tabs} ${styles.activeTabs}` : styles.tabs}
          onClick={() => setToggleState(2)}
        >
          왕십리 노선
        </div>
      </div>

      

      {/* 데이터 전달 */}
      <Station stations={stationToShow} count={count} eta={stationEtas} />


      <div style={{maxWidth: '100%', margin: '10px auto', padding: '5%'}}>
                <Ai />
              </div>
      {/* 새로고침 버튼 */}
      <RefreshButton onRefresh={fetchData} />
    </div>
    
  );
};

export default Root;
