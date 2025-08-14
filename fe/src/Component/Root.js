import React, { useState } from "react";
import Station from "./Station";
import styles from '../css/RootCss.module.css';

const Root = () => {
  const [toggleState, setToggleState] = useState(1);

  const toggleTab = (index) => {
    setToggleState(index);
  };

  return (
    <div>
      <div className={styles.blocTabs}>
  <div
    className={
      toggleState === 1
        ? `${styles.tabs} ${styles.activeTabs}`
        : styles.tabs
    }
    onClick={() => toggleTab(1)}
  >
    한양대/마장 노선
  </div>
  <div
    className={
      toggleState === 2
        ? `${styles.tabs} ${styles.activeTabs}`
        : styles.tabs
    }
    onClick={() => toggleTab(2)}
  >
    왕십리 노선
  </div>
</div>


      <div className={styles.contentTabs}>
        <div
          className={
            toggleState === 1
              ? `${styles.content} ${styles.activeContent}`
              : styles.content
          }
        >
          <Station />
        </div>
      </div>
    </div>
  );
};

export default Root;
