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
      <div className="app-wrapper">
      <div className="mockup">
        <div className="mockup-inner">
          {/* 콘텐츠 자리 */} 
          <Header/>
          <Root />
              <div style={{maxWidth: '100%', margin: '10px auto', padding: '5%'}}>
                <Ai />
              </div>
          <Refresh />
        </div>
      </div>
    </div>
     
    </div>
  );
}

export default App;
