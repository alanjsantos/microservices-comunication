import bcrypt from 'bcrypt';
import User from '../../modules/user/model/User.js';

export async function createInitialData() {
    
    try {
        
        await User.sync({force: true});
        let passwrod = await bcrypt.hash('123456', 10)
        
        await User.create({
            name: 'User test',
            email: 'teste@teste.com',
            password: '123456',
        })
    } catch (err) {
        console.log(err);
    }
}