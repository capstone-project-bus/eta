
// 챗지피티 ^.^ !!~~ 데모 코드 

import React from "react";
import { Bar } from "react-chartjs-2";
import {
  Chart as ChartJS,
  CategoryScale,
  LinearScale,
  BarElement,
  Title,
  Tooltip,
  Legend,
} from "chart.js";

ChartJS.register(CategoryScale, LinearScale, BarElement, Title, Tooltip, Legend);

// X축: 09시 ~ 22시 (1시간 단위)
const labels = [
  "09시", "10시", "11시", "12시", "13시", "14시",
  "15시", "16시", "17시", "18시", "19시", "20시", "21시", "22시"
];

// 데모 데이터 (14개)
const hyMaDemo = [6, 8, 12, 15, 10, 7, 9, 14, 20, 18, 12, 8, 5, 3];
const wangDemo = [4, 6, 9, 11, 7, 6, 8, 12, 16, 14, 10, 7, 4, 2];

const data = {
  labels,
  datasets: [
    {
      label: "한양대/마장 노선",
      data: hyMaDemo,
      backgroundColor: "rgba(54, 162, 235, 0.6)",
      borderRadius: 4,
    },
    {
      label: "왕십리 노선",
      data: wangDemo,
      backgroundColor: "rgba(255, 99, 132, 0.6)",
      borderRadius: 4,
    },
  ],
};

const options = {
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    title: { display: true, text: "시간대별 탑승 인원 (1시간 간격)" },
    legend: { position: "top" },
    tooltip: {
      callbacks: {
        label: (ctx) => `${ctx.dataset.label}: ${ctx.parsed.y}명`,
      },
    },
  },
  scales: {
    x: {
      grid: { display: false },
    },
    y: {
      beginAtZero: true,
      title: { display: true, text: "탑승 인원(명)" },
      ticks: { precision: 0 },
    },
  },
};

export default function Ai() {
  return (
    <div style={{ height: 360 }}>
      <Bar options={options} data={data} />
    </div>
  );
}
