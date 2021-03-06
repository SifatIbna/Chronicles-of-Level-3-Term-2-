#include <iostream>
#include <vector>
#include <limits>
#include <iomanip>
#include <cstdlib>
#include <ctime>

using namespace std;

void display(vector<vector<int>> Output, vector<bool> CacheHit,int Fsize,vector<int> InputData)
{
    for (int i=Fsize-1; i>=0;i--){
        cout << "|"<<"i ="<<i;
    }
    cout <<"|"<<"Inputs"<<"|"<<"Hit"<<"\n";
    int HitCount = 0;
    for (int i = 0; i < Output.size(); i++)
    {


        for (int j = 0; j < Output[i].size(); j++)
            cout << "|" << setw(4) << Output[i][j];
        cout << "| "<<setw(5)<<InputData[i]<<"| ";
        if (CacheHit[i] == true)
        {
            cout << "*";
            HitCount++;
        }

        cout << endl;
    }


    cout << endl;
    cout << "Number of Hits         :   " << HitCount << endl;
    cout << "Hit Ratio              :   " << double(HitCount)/Output.size() <<endl;

}


void FIFOmanual(int AddressLength, int Fsize)
{
    int HitCount = 0;
    vector<int> FrameSize;

    cout << endl;
    cout << "THIS IS THE MANUAL FIFO METHOD !!" << endl;
    cout << endl;
    FrameSize.resize(Fsize, 0);
    cout << "Enter your desired Input :  " << endl;

    vector<int> InputData(AddressLength);

    for(int i = 0; i < AddressLength; i++)
    {
        cin>>InputData.at(i);
    }

    cout<<"User Inputted Address : ";
    for(int i = 0; i < AddressLength; i++)
    {
        cout<<InputData[i]<<" ";
    }
    cout << endl;
    cout << endl;

    vector < vector<int>> Output;
    vector<bool> CacheHit;

    for (int i=0; i<InputData.size(); i++)
    {
        bool CacheMiss = true;
        for (int j=0; j<FrameSize.size(); j++)
        {
            if (InputData[i] == FrameSize[j])
            {
                HitCount++;
                CacheMiss = false;
                break;
            }
        }
        if (CacheMiss)
        {
            FrameSize.erase(FrameSize.begin());
            FrameSize.push_back(InputData[i]);
        }
        Output.push_back(FrameSize);
        CacheHit.push_back((CacheMiss == false));
    }

    display(Output, CacheHit,Fsize,InputData);
}

void FIFOauto(int AddressLength, int Fsize)
{
    int HitCount = 0;
    vector<int> FrameSize;
    int LargestNumber;
    int SmallestNumber = 1;

    cout << endl;
    cout << "THIS IS THE AUTOMATIC FIFO METHOD !!" << endl;
    cout << endl;
    FrameSize.resize(Fsize, 0);
    cout << "Largest Possible Number : ";
    cin >> LargestNumber;

    vector<int> InputData(AddressLength);
    for(int i = 0; i < AddressLength; i++)
    {
        InputData[i] = rand() % (LargestNumber + 1 - SmallestNumber) + SmallestNumber;;
    }
    cout<<"Inputted Address : ";
    for(int i = 0; i < AddressLength; i++)
    {
        cout<<InputData[i]<<" ";
    }
    cout << endl;
    cout << endl;

    vector < vector<int>> Output;
    vector<bool> CacheHit;

    for (int i=0; i<InputData.size(); i++)
    {
        bool CacheMiss = true;
        for (int j=0; j<FrameSize.size(); j++)
        {
            if (InputData[i] == FrameSize[j])
            {
                HitCount++;
                CacheMiss = false;
                break;
            }
        }
        if (CacheMiss)
        {
            FrameSize.erase(FrameSize.begin());
            FrameSize.push_back(InputData[i]);
        }
        Output.push_back(FrameSize);
        CacheHit.push_back((CacheMiss == false));
    }
    display(Output, CacheHit,Fsize,InputData);
}


void LRUUser(int AddressLength, int Fsize)
{
    int HitCount = 0;
    vector<int> FrameSize;

    cout << endl;
    cout << "THIS IS THE MANUAL LRU METHOD !!" << endl;
    cout << endl;
    FrameSize.resize(Fsize, 0);
    cout << "Enter your desired Input :  " << endl;
    vector<int> InputData(AddressLength);

    for(int i = 0; i < AddressLength; i++)
    {
        cin>>InputData.at(i);
    }

    cout<<"User Inputted Address : ";

    for(int i = 0; i < AddressLength; i++)
    {
        cout<<InputData[i]<<" ";
    }
    cout << endl;
    cout << endl;
    vector < vector<int>> Output;
    vector<bool> CacheHit;


    for (int i=0; i<InputData.size(); i++)
    {
        bool CacheMiss = true;
        for (int j=0; j<FrameSize.size(); j++)
        {
            if (InputData[i] == FrameSize[j])
            {
                //if cache hit
                FrameSize.erase(FrameSize.begin() + j);
                FrameSize.push_back(InputData[i]);
                HitCount++;
                CacheMiss = false;
                break;
            }

        }
        if (CacheMiss)
        {
            FrameSize.erase(FrameSize.begin());
            FrameSize.push_back(InputData[i]);
        }

        Output.push_back(FrameSize);
        CacheHit.push_back((CacheMiss == false));
    }

    display(Output, CacheHit,Fsize,InputData);

}

void LRUauto(int AddressLength, int Fsize)
{
    int HitCount = 0;
    vector<int> FrameSize;
    int LargestNumber;
    int SmallestNumber = 1;

    cout << endl;
    cout << "THIS IS THE AUTOMATIC LRU METHOD !!" << endl;
    cout << endl;

    FrameSize.resize(Fsize, 0);
    cout << "Largest Possible Number : ";
    cin >> LargestNumber;

    vector<int> InputData(AddressLength);
    for(int i = 0; i < AddressLength; i++)
    {
        InputData[i] = rand() % (LargestNumber + 1 - SmallestNumber) + SmallestNumber;;
    }
    cout<<"Inputted Address : ";

    for(int i = 0; i < AddressLength; i++)
    {
        cout<<InputData[i]<<" ";
    }
    cout << endl;
    cout << endl;

    vector < vector<int>> Output;
    vector<bool> CacheHit;

    for (int i=0; i<InputData.size(); i++)
    {
        bool CacheMiss = true;
        for (int j=0; j<FrameSize.size(); j++)
        {
            if (InputData[i] == FrameSize[j])
            {
                FrameSize.erase(FrameSize.begin() + j);
                FrameSize.push_back(InputData[i]);
                HitCount++;
                CacheMiss = false;
                break;
            }

        }
        if (CacheMiss)
        {
            FrameSize.erase(FrameSize.begin());
            FrameSize.push_back(InputData[i]);
        }
        Output.push_back(FrameSize);
        CacheHit.push_back((CacheMiss == false));
    }

    display(Output, CacheHit,Fsize,InputData);
}

void Randomanual(int AddressLength, int Fsize)
{
    int HitCount = 0;
    vector<int> FrameSize;

    cout << endl;
    cout << "THIS IS THE MANUAL RANDOM METHOD !!" << endl;
    cout << endl;
    FrameSize.resize(Fsize, 0);
    cout << "Enter your desired Input :  " << endl;
    vector<int> InputData(AddressLength);
    for(int i = 0; i < AddressLength; i++)
    {
        cin>>InputData.at(i);
    }

    cout<<"User Inputted Address : ";
    for(int i = 0; i < AddressLength; i++)
    {
        cout<<InputData[i]<<" ";
    }
    cout << endl;
    cout << endl;

    vector < vector<int>> Output;
    vector<bool> CacheHit;

    for (int i=0; i<InputData.size(); i++)
    {
        bool CacheMiss = true;
        for (int j=0; j<FrameSize.size(); j++)
        {
            if (InputData[i] == FrameSize[j])
            {
                HitCount++;
                CacheMiss = false;
                break;
            }
        }
        if (CacheMiss)
        {
            if (FrameSize[0] == 0)
            {
                FrameSize.erase(FrameSize.begin());
            }
            else
            {
                FrameSize.erase(FrameSize.begin() + (rand() % Fsize));
            }
            FrameSize.push_back(InputData[i]);
        }
        Output.push_back(FrameSize);
        CacheHit.push_back((CacheMiss == false));
    }

    display(Output, CacheHit,Fsize,InputData);
}


void Randomatic(int AddressLength, int Fsize)
{
    int HitCount = 0;
    vector<int> FrameSize;
    int LargestNumber;
    int SmallestNumber = 1;

    cout << endl;
    cout << "THIS IS THE RANDOM METHOD !!" << endl;
    cout << endl;
    FrameSize.resize(Fsize, 0);
    cout << "Largest Possible Number : ";
    cin >> LargestNumber;

    vector<int> InputData(AddressLength);
    for(int i = 0; i < AddressLength; i++)
    {
        InputData[i] = rand() % (LargestNumber + 1 - SmallestNumber) + SmallestNumber;;
    }
    cout<<"Inputted Address : ";
    for(int i = 0; i < AddressLength; i++)
    {
        cout<<InputData[i]<<" ";
    }
    cout << endl;
    cout << endl;

    vector < vector<int>> Output;
    vector<bool> CacheHit;

    for (int i=0; i<InputData.size(); i++)
    {
        bool CacheMiss = true;
        for (int j=0; j<FrameSize.size(); j++)
        {
            if (InputData[i] == FrameSize[j])
            {
                HitCount++;
                CacheMiss = false;
                break;
            }
        }
        if (CacheMiss)
        {
            if (FrameSize[0] == 0)
            {
                FrameSize.erase(FrameSize.begin());
            }
            else
            {
                FrameSize.erase(FrameSize.begin() + (rand() % Fsize));
            }
            FrameSize.push_back(InputData[i]);
        }
        Output.push_back(FrameSize);
        CacheHit.push_back((CacheMiss == false));
    }
    display(Output, CacheHit,Fsize,InputData);
}


int main()
{
    int alg;
    srand(int (time(0)));
    int AddressLength = 0;
    int Fsize = 0;

    cout << "Enter the maximum number of input :  " << endl;
    cin >>AddressLength;
    cout << "Enter the frame size you want : " << endl;
    cin >> Fsize;
    cout<<"Please pick what algorithm do u want to use!"<<endl;
    cout<<"1.FIFO"<<endl;
    cout<<"2.LRU"<<endl;
    cout<<"3.RANDOM"<<endl;
    cin>>alg;

    int mantic;
    switch(alg)
    {
        case 1 :
            cout << " Would you like to use Manual FIFO(1) or Automatic FIFO(2)? "<< endl;
            cin >> mantic;

            switch(mantic)
            {
                case 1 :
                    FIFOmanual(AddressLength, Fsize);
                    break;
                case 2 :
                    FIFOauto(AddressLength, Fsize);
                    break;
            }

            break;

        case 2 :
            cout << " Would you like to use Manual LRU(1) or Automatic LRU(2)? " << endl;
            cin >> mantic;

            switch(mantic)
            {
                case 1 :
                    LRUUser(AddressLength, Fsize);
                    break;
                case 2 :
                    LRUauto(AddressLength, Fsize);
                    break;
            }
            break;
        case 3 :
            cout << " Would you like to use Manual RANDOM(1) or Automatic RANDOM(2)? " << endl;
            cin >> mantic;

            switch(mantic)
            {
                case 1 :
                    Randomanual(AddressLength, Fsize);
                    break;
                case 2 :
                    Randomatic(AddressLength, Fsize);
                    break;
            }
            break;
        default:
            cout<<"Option is invalid"<<endl;
            cout<<endl;

            system("cls");
            exit(0);
    }
    return 0;
}

/*

1
2
1000000
4
9

*/
