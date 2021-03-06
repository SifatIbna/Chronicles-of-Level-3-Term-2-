import pandas as pd
import matplotlib.pyplot as plt

grid_size = [250,500,750,1000,1250]
node = [20,40,60,80,100]
flow = [10,20,30,40,50]

def show_graph(csv_file,type_):
    
    data = pd.read_csv(csv_file)
    data.head()
    
    throughput = list(data['throughput'])
    delay = list(data['delay'])
    delivery_ratio = list(data['delivery_ratio'])
    drop_ratio = list(data['drop_ratio'])
    
    plt.plot(grid_size,throughput,color='green', linewidth = 3, 
            marker='o', markerfacecolor='blue', markersize=12)
    plt.ylabel("Throughput")
    plt.xlabel(type_)
    plt.title(type_+" vs Throughput")
    plt.show()


    # ## Graph on *Grid Size VS Delay*

    # In[79]:


    plt.plot(grid_size,delay,color='green', linewidth = 3, 
            marker='o', markerfacecolor='blue', markersize=12)
    plt.ylabel("Delay")
    plt.xlabel(type_)
    plt.title(type_+" vs Delay")
    plt.show()


    # ## Graph on *Grid Size VS Delivery Ratio*

    # In[80]:


    plt.plot(grid_size,delivery_ratio,color='green', linewidth = 3, 
            marker='o', markerfacecolor='blue', markersize=12)
    plt.ylabel("Delivery Ratio")
    plt.xlabel(type_)
    plt.title(type_+" vs Delivery Ratio")
    plt.show()


    # ## Graph on *Grid Size VS Drop Ratio*

    # In[81]:


    plt.plot(grid_size,drop_ratio,color='green', linewidth = 3, 
            marker='o', markerfacecolor='blue', markersize=12)
    plt.ylabel("Drop Ratio")
    plt.xlabel(type_)
    plt.title(type_+" vs Drop Ratio")
    plt.show()

show_graph("outputgrid.csv","Grid")
show_graph("outputflow.csv","Flow")
show_graph("outputnode.csv","Node")