import React from "react";

function Header() {
    const style = {
        color: 'black',
        fontSize: '19px', /*ios default font size는 17pt, 헤더여서 19px로 설정함 */
        fontWeight: '600',
        margin: '10% 5% 5% 5%' /*상 우 하 좌 */
    }
    return(
        <div style={style}>셔틀 버스(한양여자대학교)</div>
    );
    
}

export default Header;