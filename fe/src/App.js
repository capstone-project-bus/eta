import './App.css';
import Header from './Component/Header';
import Root from './Component/Root';


function App() {
  return (
    <div className="App">
      <div className="app-wrapper">
      <div className="mockup">
        <div className="mockup-inner">
          {/* 콘텐츠 자리 */} 
          <div className="contents">
            <Header/>
            <Root />
          </div> 
        </div>
      </div>
    </div>
    </div>
  );
}

export default App;
