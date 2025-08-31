import React from "react";
import styles from '../css/RootCss.module.css';

const Refresh = ({ onRefresh }) => {
  return (
    <button className={styles.refreshButton} onClick={onRefresh}>
      ↺
    </button>
  );
};

export default Refresh;
