import React from "react";
import { Bar } from 'react-chartjs-2';
import {
  Chart as ChartJS,
  CategoryScale,  // x축 (카테고리)
  LinearScale,    // y축 (수치)
  BarElement,
  Title,
  Tooltip,
  Legend,
} from 'chart.js';

ChartJS.register(
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend
);
const options = {
  responsive: true,
  maintainAspectRatio: false, // 부모 높이에 맞춰 늘어나게
  plugins: {
    title: {
      display: true,
      text: '노선별 탑승 인원',
    },
    legend: {
      position: 'top',
    },
  },
  scales: {
    y: { beginAtZero: true },
}


};

const labels = ['월', '화', '수', '목', '금'];

const data = {
  labels,
  datasets: [
    {
      label: '한양대/마장 노선',
      data: [12, 19, 7, 10, 16],
    },
    {
      label: '왕십리 노선',
      data: [8, 11, 13, 6, 9],
    },
  ],
};


 

export default function Ai(){
    return(
        <div style={{height: 200, }}>
            <Bar options={options} data={data}/>
        </div>
);
}