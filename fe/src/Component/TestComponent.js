import React, { useEffect } from 'react';

import { useState } from 'react';

// 일단 COUNT 값만 확인해보는 컴포넌트
function TestComponent(){

    const [count, setCount] = useState(0);

  useEffect(() => {
    const fetchCount = async () => {
      try {
        const res = await fetch("http://localhost:8080/api/count");
        const data = await res.text(); // int 하나니까 일단 text (json은 차차...)
        setCount(parseInt(data));
      } catch (err) {
        console.error("error", err);
      }
    };

    fetchCount(); // 최초 1회 실행
    const interval = setInterval(fetchCount, 15000); // 15초마다 polling

    return () => clearInterval(interval); // 언마운트 시..
  }, []);

  return (
    <div>
      <h1>현재 인원 수: {count}명</h1>
    </div>
  );
}

export default TestComponent;