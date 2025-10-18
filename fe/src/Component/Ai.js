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

const labels = [
  "09시", "10시", "11시", "12시", "13시", "14시",
  "15시", "16시", "17시", "18시", "19시", "20시", "21시", "22시"
];

const hyMaDemo = [6, 8, 12, 15, 10, 7, 9, 14, 20, 18, 12, 8, 5, 3];
const wangDemo = [4, 6, 9, 11, 7, 6, 8, 12, 16, 14, 10, 7, 4, 2];

const data = {
  labels,
  datasets: [
    {
      label: "한양대/마장 노선",
      data: hyMaDemo,
      backgroundColor: "rgba(216, 227, 227, 0.85)",
      hoverBackgroundColor: "rgba(108, 121, 144, 1)",
      borderRadius: 8,
      borderWidth: 0,
    },
    {
      label: "왕십리 노선",
      data: wangDemo,
      backgroundColor: "rgba(255, 218, 246, 0.85)",
      hoverBackgroundColor: "rgba(252, 173, 238, 1)",
      borderRadius: 8,
      borderWidth: 0,
    },
  ],
};

const options = {
  responsive: true,
  maintainAspectRatio: false,
  plugins: {
    title: {
      display: true,
      text: "ai 분석 시간대별 탑승 인원 예측",
      font: {
        size: 15,
        weight: "600",
        family: "'Pretendard', -apple-system, sans-serif"
      },
      color: "#1f2937",
      padding: { top: 16, bottom: 24 }
    },
    legend: {
      position: "top",
      align: "end",
      labels: {
        font: {
          size: 13,
          family: "'Pretendard', -apple-system, sans-serif"
        },
        color: "#4b5563",
        padding: 16,
        usePointStyle: true,
        pointStyle: "circle",
        boxWidth: 8,
        boxHeight: 8
      }
    },
    tooltip: {
      backgroundColor: "rgba(17, 24, 39, 0.95)",
      titleColor: "#fff",
      bodyColor: "#fff",
      titleFont: {
        size: 14,
        weight: "600",
        family: "'Pretendard', -apple-system, sans-serif"
      },
      bodyFont: {
        size: 13,
        family: "'Pretendard', -apple-system, sans-serif"
      },
      padding: 12,
      cornerRadius: 8,
      displayColors: true,
      callbacks: {
        label: (ctx) => `${ctx.dataset.label}: ${ctx.parsed.y}명`,
      },
    },
  },
  scales: {
    x: {
      grid: {
        display: false,
      },
      ticks: {
        font: {
          size: 11,
          family: "'Pretendard', -apple-system, sans-serif"
        },
        color: "#6b7280"
      },
      border: {
        display: false
      }
    },
    y: {
      beginAtZero: true,
      title: {
        display: true,
        text: "탑승 인원(명)",
        font: {
          size: 13,
          weight: "500",
          family: "'Pretendard', -apple-system, sans-serif"
        },
        color: "#4b5563"
      },
      ticks: {
        precision: 0,
        font: {
          size: 11,
          family: "'Pretendard', -apple-system, sans-serif"
        },
        color: "#6b7280",
        padding: 8
      },
      grid: {
        color: "rgba(229, 231, 235, 0.8)",
        lineWidth: 1
      },
      border: {
        display: false,
        dash: [4, 4]
      }
    },
  },
  layout: {
    padding: {
      left: 8,
      right: 8,
      top: 0,
      bottom: 8
    }
  },
  interaction: {
    mode: 'index',
    intersect: false,
  }
};

export default function Ai() {
  return (
    <div style={{
      height: 400,
      padding: "24px 16px",
      background: "linear-gradient(to bottom, #fafafa, #ffffff)",
      borderRadius: "16px"
    }}>
      <Bar options={options} data={data} />
    </div>
  );
}