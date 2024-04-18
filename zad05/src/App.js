import React from 'react';
import { BrowserRouter as Router, Route, Routes, Link } from 'react-router-dom';
import Produkty from './components/Produkty';
import Koszyk from './components/Koszyk';
import Platnosci from './components/Platnosci';


const App = () => {
    return (
        <Router>
            <div>
                <ul>
                    <li>
                        <Link to="/produkty">Produkty</Link>
                    </li>
                    <li>
                        <Link to="/koszyk/1">Koszyk</Link>
                    </li>
                    <li>
                        <Link to="/platnosci">Płatności</Link>
                    </li>
                </ul>
                <Routes>
                    <Route path="/produkty" element={<Produkty />} />
                    <Route path="/koszyk/1" element={<Koszyk />} />
                    <Route path="/platnosci" element={<Platnosci />} />
                </Routes>
            </div>
        </Router>
    );
};

export default App;
