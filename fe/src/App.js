import logo from './logo.svg';
import './App.css';
import axios from "axios";
import TestComponent from './Component/TestComponent';
import Header from './Component/Header';
import Root from './Component/Root';
import Ai from './Component/Ai';
import Refresh from './Component/Refresh';


function App() {
  return (
    <div className="App">
      <Header/>
      <Root />
      <div style={{maxWidth: 500, margin: '10px auto', padding: '5%', }}>
          <Ai />
      </div>
      <Refresh />
    </div>
  );
}

export default App;
